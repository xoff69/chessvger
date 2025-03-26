import axios from 'axios';
import { getToken } from '../services/authService.js';
export async function sendPostRequest(url, databaseId) {
  
  const token=getToken();
  try {

    const data = { databaseId };
    console.log("databaseId à envoyer:", data);
    const config = {
      headers: {
        'Content-Type': 'application/json', 
        Authorization: token,
      },
    };
    console.log("config",config);

    const response = await axios.post(url, data, config);
    return response;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Une erreur est survenue.');
  }
}
export async function sendGetRequest(url) {

  
  try {
    const config = {
      headers: {
        Authorization: getToken(),
      },
    };
    const response = await axios.get(url,config);
    return response;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Une erreur est survenue.');
  }
}
