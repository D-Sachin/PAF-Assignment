import React, { useState } from "react";
import { Search, Filter, X } from "lucide-react";

/**
 * ResourceFilterBar Component - Member 1 Module
 * Allows users to search and filter resources
 */
const MemberOneResourceFilterBar = ({ onFilter, onSearch, isLoading }) => {
  const [searchTerm, setSearchTerm] = useState("");
  const [showAdvanced, setShowAdvanced] = useState(false);
  const [filters, setFilters] = useState({
    type: "",
    status: "",
    location: "",
    minCapacity: "",
    maxCapacity: "",
  });

  const resourceTypes = [
    { value: "", label: "All Types" },
    { value: "LECTURE_HALL", label: "🎓 Lecture Hall" },
    { value: "LAB", label: "🔬 Laboratory" },
    { value: "MEETING_ROOM", label: "👥 Meeting Room" },
    { value: "EQUIPMENT", label: "⚙️ Equipment" },
  ];

  const statuses = [
    { value: "", label: "All Status" },
    { value: "ACTIVE", label: "✓ Active" },
    { value: "OUT_OF_SERVICE", label: "✗ Out of Service" },
  ];

  const handleSearch = (e) => {
    e.preventDefault();
    onSearch(searchTerm);
  };

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilters((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleApplyFilters = () => {
    onFilter(filters);
    setShowAdvanced(false);
  };

  const handleClearFilters = () => {
    setFilters({
      type: "",
      status: "",
      location: "",
      minCapacity: "",
      maxCapacity: "",
    });
    setSearchTerm("");
    onSearch("");
    onFilter({
      type: "",
      status: "",
      location: "",
      minCapacity: "",
      maxCapacity: "",
    });
  };

  const hasActiveFilters =
    Object.values(filters).some((v) => v !== "") || searchTerm !== "";

  return (
    <div className="bg-white rounded-lg shadow-md p-6 mb-6 border-t-4 border-blue-500">
      {/* Search Bar */}
      <form onSubmit={handleSearch} className="mb-4">
        <div className="flex gap-2">
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-3 w-5 h-5 text-gray-400" />
            <input
              type="text"
              placeholder="Search resources by name..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              disabled={isLoading}
              className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50"
            />
          </div>
          <button
            type="submit"
            disabled={isLoading}
            className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50"
          >
            🔍 Search
          </button>
          <button
            type="button"
            onClick={() => setShowAdvanced(!showAdvanced)}
            className="px-6 py-2 bg-gray-600 text-white rounded-lg hover:bg-gray-700 transition-colors flex items-center gap-2"
          >
            <Filter className="w-5 h-5" />
            Advanced
          </button>
        </div>
      </form>

      {/* Advanced Filters */}
      {showAdvanced && (
        <div className="bg-gray-50 p-5 rounded-lg border border-gray-200 mb-4">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mb-4">
            {/* Type Filter */}
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                Resource Type
              </label>
              <select
                name="type"
                value={filters.type}
                onChange={handleFilterChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                {resourceTypes.map((type) => (
                  <option key={type.value} value={type.value}>
                    {type.label}
                  </option>
                ))}
              </select>
            </div>

            {/* Status Filter */}
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                Status
              </label>
              <select
                name="status"
                value={filters.status}
                onChange={handleFilterChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                {statuses.map((status) => (
                  <option key={status.value} value={status.value}>
                    {status.label}
                  </option>
                ))}
              </select>
            </div>

            {/* Location Filter */}
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                Location
              </label>
              <input
                type="text"
                name="location"
                placeholder="e.g., Building A"
                value={filters.location}
                onChange={handleFilterChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            {/* Min Capacity */}
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                Min Capacity
              </label>
              <input
                type="number"
                name="minCapacity"
                placeholder="0"
                value={filters.minCapacity}
                onChange={handleFilterChange}
                min="0"
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            {/* Max Capacity */}
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                Max Capacity
              </label>
              <input
                type="number"
                name="maxCapacity"
                placeholder="500"
                value={filters.maxCapacity}
                onChange={handleFilterChange}
                max="500"
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
          </div>

          {/* Filter Actions */}
          <div className="flex gap-3 justify-end">
            {hasActiveFilters && (
              <button
                type="button"
                onClick={handleClearFilters}
                className="px-4 py-2 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-100 transition-colors flex items-center gap-2"
              >
                <X className="w-4 h-4" />
                Clear All
              </button>
            )}
            <button
              type="button"
              onClick={handleApplyFilters}
              className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
            >
              Apply Filters
            </button>
          </div>
        </div>
      )}

      {/* Active Filters Display */}
      {hasActiveFilters && (
        <div className="flex flex-wrap gap-2 items-center">
          <span className="text-sm font-semibold text-gray-700">
            Active Filters:
          </span>
          {searchTerm && (
            <span className="px-3 py-1 bg-blue-100 text-blue-800 rounded-full text-sm flex items-center gap-2">
              Search: "{searchTerm}"
              <button
                onClick={() => {
                  setSearchTerm("");
                  onSearch("");
                }}
                className="text-blue-600 hover:text-blue-800"
              >
                ✕
              </button>
            </span>
          )}
          {filters.type && (
            <span className="px-3 py-1 bg-purple-100 text-purple-800 rounded-full text-sm flex items-center gap-2">
              Type: {filters.type}
              <button
                onClick={() => {
                  setFilters((p) => ({ ...p, type: "" }));
                }}
                className="text-purple-600 hover:text-purple-800"
              >
                ✕
              </button>
            </span>
          )}
          {filters.status && (
            <span className="px-3 py-1 bg-green-100 text-green-800 rounded-full text-sm flex items-center gap-2">
              Status: {filters.status}
              <button
                onClick={() => {
                  setFilters((p) => ({ ...p, status: "" }));
                }}
                className="text-green-600 hover:text-green-800"
              >
                ✕
              </button>
            </span>
          )}
          {filters.location && (
            <span className="px-3 py-1 bg-orange-100 text-orange-800 rounded-full text-sm flex items-center gap-2">
              Location: {filters.location}
              <button
                onClick={() => {
                  setFilters((p) => ({ ...p, location: "" }));
                }}
                className="text-orange-600 hover:text-orange-800"
              >
                ✕
              </button>
            </span>
          )}
        </div>
      )}
    </div>
  );
};

export default MemberOneResourceFilterBar;
