export interface Partner {
  id?: number;
  alias: string;
  type: string;
  direction: string;
  application?: string;
  processedFlowType: string;
  description: string;
}

export enum Direction {
  INBOUND = 'INBOUND',
  OUTBOUND = 'OUTBOUND'
}

export enum ProcessedFlowType {
  MESSAGE = 'MESSAGE',
  ALERTING = 'ALERTING',
  NOTIFICATION = 'NOTIFICATION'
}
