export const getChipStatusColor = (status: string) => {
  switch(status) {
    case 'In progress': 
      return 'primary';
    case 'Assigned': 
      return 'orange';
    case 'In review': 
      return 'indigo';
    case 'Total loss': 
      return 'error';
    case 'Repair': 
      return 'orange';
    case 'Repaired': 
      return 'teal';
    case 'New':
    default:
      return 'info';
  }
};

export const getChipStatusIcon = (status: string) => {
  switch(status) {
    case 'Assigned': 
      return 'mdi-clipboard-check';
    case 'In review': 
      return 'mdi-eye';
    case 'Total loss': 
      return 'mdi-close-circle';
    case 'Repair': 
      return 'mdi-wrench';
    case 'Repaired': 
      return 'mdi-check';
    case 'New':
    case 'In progress': 
    default:
      return 'mdi-progress-clock';
  }
};