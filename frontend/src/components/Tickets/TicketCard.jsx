import React from 'react';
import { useNavigate } from 'react-router-dom';
import { getPriorityColor, getStatusColor } from '../../utils/ticketUtils';

const TicketCard = ({ ticket }) => {
  const navigate = useNavigate();

  const formattedDate = new Date(ticket.createdAt).toLocaleDateString('en-GB', {
    day: 'numeric',
    month: 'short',
    year: 'numeric',
  });

  return (
    <div 
      className="bg-white rounded-lg shadow-sm border border-gray-200 p-5 hover:shadow-md transition-shadow cursor-pointer"
      onClick={() => navigate(`/tickets/${ticket.id}`)}
    >
      <div className="flex justify-between items-start mb-3">
        <h3 className="text-lg font-semibold text-gray-900 line-clamp-1">{ticket.title}</h3>
        <span className={`px-2.5 py-0.5 rounded-full text-xs font-medium border ${getStatusColor(ticket.status)}`}>
          {ticket.status?.replace('_', ' ')}
        </span>
      </div>
      
      <p className="text-gray-600 text-sm mb-4 line-clamp-2">{ticket.description}</p>
      
      <div className="flex justify-between items-center text-sm">
        <div className="flex items-center gap-3">
          <span className={`px-2 py-1 rounded text-xs font-medium ${getPriorityColor(ticket.priority)}`}>
            {ticket.priority}
          </span>
          <span className="text-gray-500">
            ID: #{ticket.id}
          </span>
        </div>
        <div className="text-gray-500">
          {formattedDate}
        </div>
      </div>
    </div>
  );
};

export default TicketCard;
