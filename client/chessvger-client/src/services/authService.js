export function getToken() {
  return localStorage.getItem('authToken');
}

export function getUser() {
  const user = localStorage.getItem('user');
  return user ? JSON.parse(user) : null;
}

export function logout() {
  localStorage.removeItem('authToken');
  localStorage.removeItem('user');
}
