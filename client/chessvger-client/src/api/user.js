let mockUser = {
    tenantId: 1,
    name: "John Doe",
    email: "john.doe@example.com",
    token: "mockToken123"
  };

  export const login = async (email, password) => {
    // Simuler une authentification
    if (email === "john@doe" && password === "password") {
      return Promise.resolve(mockUser);
    } else {
      return Promise.reject(new Error("Invalid credentials"));
    }
  };

  export const getUser = async (token) => {
    // Simuler la récupération d'utilisateur
    if (token === "mockToken123") {
      return Promise.resolve(mockUser);
    } else {
      return Promise.reject(new Error("Invalid token"));
    }
  };
