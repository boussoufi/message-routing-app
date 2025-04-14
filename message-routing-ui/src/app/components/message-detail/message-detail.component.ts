import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {Message} from '../../models/message.model';

@Component({
  selector: 'app-message-detail',
  standalone: false,
  templateUrl: './message-detail.component.html',
  styleUrl: './message-detail.component.scss'
})
export class MessageDetailComponent {
  constructor(
    public dialogRef: MatDialogRef<MessageDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { message: Message }
  ) {}

  close(): void {
    this.dialogRef.close();
  }
}
