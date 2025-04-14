import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import {Partner} from '../../models/partner.model';
import {PartnerService} from '../../services/partner.service';
import {PartnerFormComponent} from '../../components/partner-form/partner-form.component';
import {ConfirmationDialogComponent} from '../../components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-partners',
  standalone: false,
  templateUrl: './partners.component.html',
  styleUrl: './partners.component.scss'
})
export class PartnersComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['id', 'alias', 'type', 'direction', 'application', 'processedFlowType', 'description', 'actions'];
  dataSource = new MatTableDataSource<Partner>();
  isLoading = true;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private partnerService: PartnerService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadPartners();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadPartners(): void {
    this.isLoading = true;
    this.partnerService.getPartners().subscribe({
      next: (partners) => {
        console.log('partners reçus:', partners);
        this.dataSource.data = partners;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading partners', error);
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

  openAddPartnerDialog(): void {
    const dialogRef = this.dialog.open(PartnerFormComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.createPartner(result);
      }
    });
  }

  createPartner(partner: Partner): void {
    this.partnerService.createPartner(partner).subscribe({
      next: (newPartner) => {
        this.dataSource.data = [...this.dataSource.data, newPartner];
        this.snackBar.open('Partenaire ajouté avec succès', 'Fermer', {
          duration: 3000
        });
      },
      error: (error) => {
        console.error('Error creating partner', error);
        this.snackBar.open('Erreur lors de l\'ajout du partenaire', 'Fermer', {
          duration: 3000
        });
      }
    });
  }

  deletePartner(partner: Partner): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {
        title: 'Confirmer la suppression',
        message: `Êtes-vous sûr de vouloir supprimer le partenaire "${partner.alias}" ?`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.partnerService.deletePartner(partner.id!).subscribe({
          next: () => {
            this.dataSource.data = this.dataSource.data.filter(p => p.id !== partner.id);
            this.snackBar.open('Partenaire supprimé avec succès', 'Fermer', {
              duration: 3000
            });
          },
          error: (error) => {
            console.error('Error deleting partner', error);
            this.snackBar.open('Erreur lors de la suppression du partenaire', 'Fermer', {
              duration: 3000
            });
          }
        });
      }
    });
  }
}
