import React, { useState, useEffect } from "react";
import { X } from "lucide-react";

/**
 * ResourceForm Component - Member 1 Module
 * Form for creating and editing resources
 */
const MemberOneResourceForm = ({ resource, onSubmit, onCancel, isLoading }) => {
  const [formData, setFormData] = useState({
    name: "",
    type: "LECTURE_HALL",
    capacity: 30,
    location: "",
    description: "",
    status: "ACTIVE",
    availableFrom: "",
    availableTo: "",
  });

  const [errors, setErrors] = useState({});

  // Populate form with existing resource data if editing
  useEffect(() => {
    if (resource) {
      setFormData({
        name: resource.name || "",
        type: resource.type || "LECTURE_HALL",
        capacity: resource.capacity || 30,
        location: resource.location || "",
        description: resource.description || "",
        status: resource.status || "ACTIVE",
        availableFrom: resource.availableFrom || "",
        availableTo: resource.availableTo || "",
      });
    }
  }, [resource]);

  const resourceTypes = [
    { value: "LECTURE_HALL", label: "🎓 Lecture Hall" },
    { value: "LAB", label: "🔬 Laboratory" },
    { value: "MEETING_ROOM", label: "👥 Meeting Room" },
    { value: "EQUIPMENT", label: "⚙️ Equipment" },
  ];

  const statuses = [
    { value: "ACTIVE", label: "✓ Active" },
    { value: "OUT_OF_SERVICE", label: "✗ Out of Service" },
  ];

  const validateForm = () => {
    const newErrors = {};

    if (!formData.name.trim()) {
      newErrors.name = "Resource name is required";
    } else if (formData.name.length < 3) {
      newErrors.name = "Name must be at least 3 characters";
    }

    if (!formData.location.trim()) {
      newErrors.location = "Location is required";
    }

    if (formData.capacity < 1 || formData.capacity > 500) {
      newErrors.capacity = "Capacity must be between 1 and 500";
    }

    if (formData.availableFrom && formData.availableTo) {
      if (formData.availableFrom >= formData.availableTo) {
        newErrors.availableTo = "End time must be after start time";
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === "capacity" ? parseInt(value) || 0 : value,
    }));
    // Clear error for this field
    if (errors[name]) {
      setErrors((prev) => ({
        ...prev,
        [name]: "",
      }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
      onSubmit(formData);
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
      <div className="bg-white rounded-lg shadow-xl w-full max-w-2xl max-h-[90vh] overflow-y-auto">
        {/* Header */}
        <div className="flex items-center justify-between p-6 border-b border-gray-200 sticky top-0 bg-white">
          <h2 className="text-2xl font-bold text-gray-800">
            {resource ? "Edit Resource" : "Create New Resource"}
          </h2>
          <button
            onClick={onCancel}
            className="text-gray-500 hover:text-gray-700 transition-colors"
            disabled={isLoading}
          >
            <X className="w-6 h-6" />
          </button>
        </div>

        {/* Form */}
        <form onSubmit={handleSubmit} className="p-6 space-y-5">
          {/* Name */}
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">
              Resource Name <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              disabled={isLoading}
              className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${
                errors.name ? "border-red-500" : "border-gray-300"
              } disabled:opacity-50`}
              placeholder="e.g., Room 101 Lecture Hall"
            />
            {errors.name && (
              <p className="text-red-500 text-sm mt-1">{errors.name}</p>
            )}
          </div>

          {/* Type and Capacity */}
          <div className="grid grid-cols-2 gap-4">
            {/* Type */}
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                Resource Type <span className="text-red-500">*</span>
              </label>
              <select
                name="type"
                value={formData.type}
                onChange={handleChange}
                disabled={isLoading}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50"
              >
                {resourceTypes.map((type) => (
                  <option key={type.value} value={type.value}>
                    {type.label}
                  </option>
                ))}
              </select>
            </div>

            {/* Capacity */}
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                Capacity <span className="text-red-500">*</span>
              </label>
              <input
                type="number"
                name="capacity"
                value={formData.capacity}
                onChange={handleChange}
                disabled={isLoading}
                min="1"
                max="500"
                className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${
                  errors.capacity ? "border-red-500" : "border-gray-300"
                } disabled:opacity-50`}
              />
              {errors.capacity && (
                <p className="text-red-500 text-sm mt-1">{errors.capacity}</p>
              )}
            </div>
          </div>

          {/* Location */}
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">
              Location <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              name="location"
              value={formData.location}
              onChange={handleChange}
              disabled={isLoading}
              className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${
                errors.location ? "border-red-500" : "border-gray-300"
              } disabled:opacity-50`}
              placeholder="e.g., Building A, 1st Floor"
            />
            {errors.location && (
              <p className="text-red-500 text-sm mt-1">{errors.location}</p>
            )}
          </div>

          {/* Description */}
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">
              Description
            </label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              disabled={isLoading}
              rows="3"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50"
              placeholder="Optional description of the resource..."
            />
          </div>

          {/* Status and Availability */}
          <div className="grid grid-cols-2 gap-4">
            {/* Status */}
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                Status <span className="text-red-500">*</span>
              </label>
              <select
                name="status"
                value={formData.status}
                onChange={handleChange}
                disabled={isLoading}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50"
              >
                {statuses.map((status) => (
                  <option key={status.value} value={status.value}>
                    {status.label}
                  </option>
                ))}
              </select>
            </div>
          </div>

          {/* Availability Times */}
          <div className="bg-gray-50 p-4 rounded-lg">
            <h3 className="text-sm font-semibold text-gray-700 mb-3">
              Availability Hours (Optional)
            </h3>
            <div className="grid grid-cols-2 gap-4">
              {/* Available From */}
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Available From
                </label>
                <input
                  type="time"
                  name="availableFrom"
                  value={formData.availableFrom}
                  onChange={handleChange}
                  disabled={isLoading}
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50"
                />
              </div>

              {/* Available To */}
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Available To
                </label>
                <input
                  type="time"
                  name="availableTo"
                  value={formData.availableTo}
                  onChange={handleChange}
                  disabled={isLoading}
                  className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${
                    errors.availableTo ? "border-red-500" : "border-gray-300"
                  } disabled:opacity-50`}
                />
                {errors.availableTo && (
                  <p className="text-red-500 text-sm mt-1">
                    {errors.availableTo}
                  </p>
                )}
              </div>
            </div>
          </div>

          {/* Buttons */}
          <div className="flex gap-3 justify-end pt-4 border-t border-gray-200">
            <button
              type="button"
              onClick={onCancel}
              disabled={isLoading}
              className="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition-colors disabled:opacity-50"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={isLoading}
              className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50 flex items-center gap-2"
            >
              {isLoading ? (
                <>
                  <div className="animate-spin rounded-full h-4 w-4 border-2 border-white border-t-transparent"></div>
                  Saving...
                </>
              ) : (
                <>✓ {resource ? "Update" : "Create"} Resource</>
              )}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default MemberOneResourceForm;
