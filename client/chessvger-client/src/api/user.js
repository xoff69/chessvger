import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/apiadmin/users", // Adresse de votre serveur Node.js
});

export const login = async (login, password) => {
  try {
    const response = await api.post("/login", { login, password });
    return response.data;
  } catch (error) {
    console.log("login "+login);
    throw new Error(error.response?.data?.error || "Login failed");
  }
};

export const getUser = async (token) => {
  try {
    const response = await api.get("/user", {
      headers: {
        Authorization: token,
      },
    });
    return response.data; // Renvoie l'utilisateur en cas de succès
  } catch (error) {
    throw new Error(error.response?.data?.error || "Failed to fetch user");
  }
};
