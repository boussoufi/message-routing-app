import {Component, OnInit, ViewChild} from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import {Message} from '../../models/message.model';
import {MessageService, PageResponse} from '../../services/message.service';
import {MessageDetailComponent} from '../../components/message-detail/message-detail.component';

@Component({
  selector: 'app-messages',
  standalone: false,
  templateUrl: './messages.component.html',
  styleUrl: './messages.component.scss'
})
export class MessagesComponent implements OnInit {
  displayedColumns: string[] = ['id', 'messageId', 'source', 'receivedDate', 'status', 'actions'];
  dataSource = new MatTableDataSource<Message>();
  isLoading = true;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;

  constructor(
    private messageService: MessageService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadMessages();
  }

  loadMessages(page: number = 0, size: number = 5): void {
    this.isLoading = true;
    this.messageService.getMessages(page, size).subscribe({
      next: (response: PageResponse<Message>) => {
        this.dataSource.data = response.content;
        this.totalElements = response.totalElements;
        this.pageSize = response.size;
        this.pageIndex = response.number;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading messages', error);
        this.isLoading = false;
      }
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  pageChanged(event: PageEvent): void {
    this.loadMessages(event.pageIndex, event.pageSize);
  }

  viewMessageDetails(message: Message): void {
    this.dialog.open(MessageDetailComponent, {
      data: { message }
    });
  }
}
