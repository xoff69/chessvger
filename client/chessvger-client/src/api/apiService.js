import axios from 'axios';


export async function sendPostRequest(url, databaseId,tenantId) {
  try {

    const payload = {
      databaseId: databaseId,
      tenantId: tenantId,
    };

    const config = {
      headers: {
        'Content-Type': 'application/json', // Assurez-vous que le backend accepte ce type
      },
    };

    const response = await axios.post(url, payload, config);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Une erreur est survenue.');
  }
}
