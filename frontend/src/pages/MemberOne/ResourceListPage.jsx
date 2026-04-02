import React, { useState, useEffect } from "react";
import { Plus, AlertCircle, Loader } from "lucide-react";
import memberOneResourceService from "../../services/memberOneResourceService";
import MemberOneResourceCard from "../../components/MemberOne/ResourceCard";
import MemberOneResourceForm from "../../components/MemberOne/ResourceForm";
import MemberOneResourceFilterBar from "../../components/MemberOne/FilterBar";

/**
 * MemberOneResourceList Page - Member 1 Module
 * Main page for viewing, searching, filtering, and managing resources
 */
const MemberOneResourceList = () => {
  const [resources, setResources] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  // Pagination state
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [pageSize, setPageSize] = useState(10);

  // Form state
  const [showForm, setShowForm] = useState(false);
  const [editingResource, setEditingResource] = useState(null);

  // Check if user is admin (would come from auth context in real app)
  const [isAdmin, setIsAdmin] = useState(true); // TODO: Get from Auth Context

  // Load resources on mount and when pagination changes
  useEffect(() => {
    fetchResources();
  }, [currentPage, pageSize]);

  const fetchResources = async () => {
    setLoading(true);
    setError("");
    try {
      const response = await memberOneResourceService.getAllResources(
        currentPage,
        pageSize,
      );
      setResources(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (err) {
      setError("Failed to load resources. Please try again.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (term) => {
    setLoading(true);
    setError("");
    setCurrentPage(0);
    try {
      const response = await memberOneResourceService.searchResources(
        term,
        0,
        pageSize,
      );
      setResources(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (err) {
      setError("Search failed. Please try again.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleFilter = async (filters) => {
    setLoading(true);
    setError("");
    setCurrentPage(0);
    try {
      const response = await memberOneResourceService.advancedSearch(
        filters,
        0,
        pageSize,
      );
      setResources(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (err) {
      setError("Filter failed. Please try again.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateClick = () => {
    setEditingResource(null);
    setShowForm(true);
  };

  const handleEditClick = (resource) => {
    setEditingResource(resource);
    setShowForm(true);
  };

  const handleFormSubmit = async (formData) => {
    setLoading(true);
    setError("");
    try {
      if (editingResource) {
        // Update existing resource
        await memberOneResourceService.updateResource(
          editingResource.id,
          formData,
        );
        setSuccess("Resource updated successfully!");
      } else {
        // Create new resource
        await memberOneResourceService.createResource(formData);
        setSuccess("Resource created successfully!");
      }
      setShowForm(false);
      setEditingResource(null);
      fetchResources();
      // Clear success message after 3 seconds
      setTimeout(() => setSuccess(""), 3000);
    } catch (err) {
      setError(
        editingResource
          ? "Failed to update resource"
          : "Failed to create resource",
      );
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (resourceId) => {
    if (window.confirm("Are you sure you want to delete this resource?")) {
      setLoading(true);
      setError("");
      try {
        await memberOneResourceService.deleteResource(resourceId);
        setSuccess("Resource deleted successfully!");
        fetchResources();
        setTimeout(() => setSuccess(""), 3000);
      } catch (err) {
        setError("Failed to delete resource");
        console.error(err);
      } finally {
        setLoading(false);
      }
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto">
        {/* Header with Title and Create Button */}
        <div className="mb-8">
          <div className="flex items-center justify-between mb-2">
            <div>
              <h1 className="text-4xl font-bold text-gray-900">
                🏢 Facilities & Assets Catalogue
              </h1>
              <p className="text-gray-600 mt-2">
                Manage university resources, labs, meeting rooms, and equipment
              </p>
            </div>
            {isAdmin && (
              <button
                onClick={handleCreateClick}
                disabled={loading}
                className="flex items-center gap-2 px-6 py-3 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors disabled:opacity-50"
              >
                <Plus className="w-5 h-5" />
                Add Resource
              </button>
            )}
          </div>
        </div>

        {/* Success Message */}
        {success && (
          <div className="mb-6 p-4 bg-green-100 text-green-800 rounded-lg flex items-center gap-3 animate-pulse">
            <span className="text-xl">✓</span>
            <p>{success}</p>
          </div>
        )}

        {/* Error Message */}
        {error && (
          <div className="mb-6 p-4 bg-red-100 text-red-800 rounded-lg flex items-center gap-3">
            <AlertCircle className="w-5 h-5" />
            <p>{error}</p>
          </div>
        )}

        {/* Filter Bar */}
        <MemberOneResourceFilterBar
          onFilter={handleFilter}
          onSearch={handleSearch}
          isLoading={loading}
        />

        {/* Loading State */}
        {loading && (
          <div className="flex items-center justify-center py-12">
            <div className="text-center">
              <Loader className="w-12 h-12 text-blue-600 animate-spin mx-auto mb-4" />
              <p className="text-gray-600">Loading resources...</p>
            </div>
          </div>
        )}

        {/* Resources Grid */}
        {!loading && resources.length > 0 && (
          <>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
              {resources.map((resource) => (
                <MemberOneResourceCard
                  key={resource.id}
                  resource={resource}
                  onEdit={handleEditClick}
                  onDelete={handleDelete}
                  canEdit={isAdmin}
                />
              ))}
            </div>

            {/* Pagination */}
            <div className="flex items-center justify-between bg-white rounded-lg shadow-md p-6">
              <div className="text-sm text-gray-600">
                Page <span className="font-semibold">{currentPage + 1}</span> of{" "}
                <span className="font-semibold">{totalPages}</span>
              </div>

              <div className="flex gap-2">
                <button
                  onClick={() => setCurrentPage(Math.max(0, currentPage - 1))}
                  disabled={currentPage === 0 || loading}
                  className="px-4 py-2 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  ← Previous
                </button>

                <select
                  value={pageSize}
                  onChange={(e) => {
                    setPageSize(parseInt(e.target.value));
                    setCurrentPage(0);
                  }}
                  className="px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="5">5 per page</option>
                  <option value="10">10 per page</option>
                  <option value="20">20 per page</option>
                  <option value="50">50 per page</option>
                </select>

                <button
                  onClick={() =>
                    setCurrentPage(Math.min(totalPages - 1, currentPage + 1))
                  }
                  disabled={currentPage >= totalPages - 1 || loading}
                  className="px-4 py-2 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  Next →
                </button>
              </div>
            </div>
          </>
        )}

        {/* Empty State */}
        {!loading && resources.length === 0 && (
          <div className="bg-white rounded-lg shadow-md p-12 text-center">
            <div className="text-6xl mb-4">📦</div>
            <h3 className="text-xl font-semibold text-gray-800 mb-2">
              No resources found
            </h3>
            <p className="text-gray-600 mb-6">
              Try adjusting your search or filter criteria, or create a new
              resource.
            </p>
            {isAdmin && (
              <button
                onClick={handleCreateClick}
                className="px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors inline-flex items-center gap-2"
              >
                <Plus className="w-5 h-5" />
                Create First Resource
              </button>
            )}
          </div>
        )}
      </div>

      {/* Resource Form Modal */}
      {showForm && (
        <MemberOneResourceForm
          resource={editingResource}
          onSubmit={handleFormSubmit}
          onCancel={() => {
            setShowForm(false);
            setEditingResource(null);
          }}
          isLoading={loading}
        />
      )}
    </div>
  );
};

export default MemberOneResourceList;
