import apiClient from "./apiClient";

const notificationService = {
  getNotifications: () => apiClient.get("/notifications"),
  getUnreadCount: () => apiClient.get("/notifications/count"),
  markAsRead: (id) => apiClient.put(`/notifications/${id}/read`),
  markAllAsRead: () => apiClient.put("/notifications/read-all"),
  deleteNotification: (id) => apiClient.delete(`/notifications/${id}`),
  deleteAllNotifications: () => apiClient.delete("/notifications"),
};

export default notificationService;