import axios from 'axios';

// Set up your API URL (adjust based on your backend server's address)
const API_URL = 'http://localhost:9091/api';  // Update with your backend URL

// Function to handle login
export const loginUser = async (email, password) => {
  try {
    // Make a POST request to the backend login endpoint
    const response = await axios.post(`${API_URL}/auth/login`, 
      { email, password },
      {
        headers: {
          'Content-Type': 'application/json',  // Explicitly setting the Content-Type header
        },
      }
    );

    // Return the response data (user data or token, depending on backend response)
    return response.data;  // Contains the token and role information
  } catch (error) {
    console.error("Login error: ", error);
    throw error;  // Rethrow error to handle it in the LoginPage component
  }
};

// You can add more functions here for other API calls (e.g., registerUser, getProfile, etc.)


export const validateToken = async (token) => {
  try {
    const response = await axios.post('http://localhost:9091/api/auth/validate', {}, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;  // Return the response for further processing
  } catch (error) {
    console.error("Error validating token:", error);
    throw error;  // Throw error to be handled in the Dashboard component
  }
};