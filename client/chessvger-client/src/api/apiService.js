import axios from 'axios';
import { getToken } from '../services/authService.js';
export async function sendPostRequest(url, databaseId,tenantId) {
  
  const token=getToken();
  try {

    const payload = {
      databaseId: databaseId,
      tenantId: tenantId,
    };

    const config = {
      headers: {
        'Content-Type': 'application/json', 
        Authorization: token,
      },
    };

    const response = await axios.post(url, payload, config);
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
