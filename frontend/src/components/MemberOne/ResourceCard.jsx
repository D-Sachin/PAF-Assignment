import React from "react";
import { Trash2, Edit2, MapPin, Users, Clock, Tag } from "lucide-react";

/**
 * ResourceCard Component - Member 1 Module
 * Displays a single resource in card format
 */
const MemberOneResourceCard = ({ resource, onEdit, onDelete, canEdit }) => {
  const getStatusColor = (status) => {
    switch (status) {
      case "ACTIVE":
        return "bg-green-100 text-green-800";
      case "OUT_OF_SERVICE":
        return "bg-red-100 text-red-800";
      default:
        return "bg-gray-100 text-gray-800";
    }
  };

  const getTypeIcon = (type) => {
    switch (type) {
      case "LECTURE_HALL":
        return "🎓";
      case "LAB":
        return "🔬";
      case "MEETING_ROOM":
        return "👥";
      case "EQUIPMENT":
        return "⚙️";
      default:
        return "📦";
    }
  };

  return (
    <div className="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow p-6 border-l-4 border-blue-500">
      {/* Header with Type and Status */}
      <div className="flex items-start justify-between mb-4">
        <div className="flex items-center gap-3">
          <span className="text-3xl">{getTypeIcon(resource.type)}</span>
          <div>
            <h3 className="text-xl font-bold text-gray-800">{resource.name}</h3>
            <p className="text-sm text-gray-500">ID: {resource.id}</p>
          </div>
        </div>
        <span
          className={`px-3 py-1 rounded-full text-sm font-semibold ${getStatusColor(resource.status)}`}
        >
          {resource.status === "ACTIVE" ? "✓ Active" : "✗ Out of Service"}
        </span>
      </div>

      {/* Description */}
      {resource.description && (
        <p className="text-gray-600 text-sm mb-4 line-clamp-2">
          {resource.description}
        </p>
      )}

      {/* Details Grid */}
      <div className="grid grid-cols-2 gap-3 mb-4 text-sm">
        {/* Location */}
        <div className="flex items-center gap-2 text-gray-700">
          <MapPin className="w-4 h-4 text-blue-600" />
          <span>{resource.location}</span>
        </div>

        {/* Capacity */}
        <div className="flex items-center gap-2 text-gray-700">
          <Users className="w-4 h-4 text-green-600" />
          <span>{resource.capacity} capacity</span>
        </div>

        {/* Type Tag */}
        <div className="flex items-center gap-2 text-gray-700">
          <Tag className="w-4 h-4 text-purple-600" />
          <span>{resource.resourceTypeDisplay}</span>
        </div>

        {/* Availability */}
        {resource.availableFrom && resource.availableTo && (
          <div className="flex items-center gap-2 text-gray-700">
            <Clock className="w-4 h-4 text-orange-600" />
            <span>{resource.availabilityWindow}</span>
          </div>
        )}
      </div>

      {/* Timestamps */}
      <div className="text-xs text-gray-400 mb-4 border-t pt-3">
        <div>Created: {new Date(resource.createdAt).toLocaleDateString()}</div>
        <div>Updated: {new Date(resource.updatedAt).toLocaleDateString()}</div>
      </div>

      {/* Action Buttons */}
      {canEdit && (
        <div className="flex gap-2 justify-end">
          <button
            onClick={() => onEdit(resource)}
            className="flex items-center gap-2 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
          >
            <Edit2 className="w-4 h-4" />
            Edit
          </button>
          <button
            onClick={() => onDelete(resource.id)}
            className="flex items-center gap-2 px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition-colors"
          >
            <Trash2 className="w-4 h-4" />
            Delete
          </button>
        </div>
      )}
    </div>
  );
};

export default MemberOneResourceCard;
