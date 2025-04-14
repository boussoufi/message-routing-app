import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import {Direction, ProcessedFlowType} from '../../models/partner.model';

@Component({
  selector: 'app-partner-form',
  standalone: false,
  templateUrl: './partner-form.component.html',
  styleUrl: './partner-form.component.scss'
})
export class PartnerFormComponent implements OnInit {
  partnerForm!: FormGroup;
  directions = Object.values(Direction);
  processedFlowTypes = Object.values(ProcessedFlowType);

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<PartnerFormComponent>
  ) {}

  ngOnInit(): void {
    this.partnerForm = this.fb.group({
      alias: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      type: ['', Validators.required],
      direction: [Direction.INBOUND, Validators.required],
      application: [''],
      processedFlowType: [ProcessedFlowType.MESSAGE, Validators.required],
      description: ['', [Validators.required, Validators.maxLength(255)]]
    });
  }

  onSubmit(): void {
    if (this.partnerForm.valid) {
      this.dialogRef.close(this.partnerForm.value);
    }
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
