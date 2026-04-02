export const getPriorityColor = (priority) => {
  switch (priority) {
    case 'LOW':
      return 'bg-green-100 text-green-800 border-green-200';
    case 'MEDIUM':
      return 'bg-yellow-100 text-yellow-800 border-yellow-200';
    case 'HIGH':
      return 'bg-orange-100 text-orange-800 border-orange-200';
    case 'CRITICAL':
      return 'bg-red-100 text-red-800 border-red-200 animate-pulse'; // CRITICAL pulses
    default:
      return 'bg-gray-100 text-gray-800 border-gray-200';
  }
};

export const getStatusColor = (status) => {
  switch (status) {
    case 'OPEN':
      return 'bg-blue-100 text-blue-800 border-blue-200';
    case 'IN_PROGRESS':
      return 'bg-yellow-100 text-yellow-800 border-yellow-200';
    case 'RESOLVED':
      return 'bg-green-100 text-green-800 border-green-200';
    case 'CLOSED':
      return 'bg-gray-100 text-gray-800 border-gray-200';
    default:
      return 'bg-gray-100 text-gray-800 border-gray-200';
  }
};
