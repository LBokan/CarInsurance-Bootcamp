export const BASE_API_URL="http://localhost:8080";

export const HTTP_METHODS = {
  get: "GET",
  put: "PUT",
  post: "POST",
  delete: "DELETE"
}

export const ROLES = {
  client: "ROLE_CLIENT",
  insurance: "ROLE_INSURANCE_MANAGER",
  repair: "ROLE_REPAIR_MANAGER"
}

export const eventBusNames = {
  fetchAssignments: "assignments",
  fetchAssignment: "assignment",
  fetchComments: "comment"
}