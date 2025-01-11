import axios from 'axios';

// Set up your API URL (adjust based on your backend server's address)
const API_URL = 'http://localhost:9091/api';  // Update with your backend URL

// Function to handle login
export const loginUser = async (email, password) => {
  try {
    // Make a POST request to the backend login endpoint
    const response = await axios.post(`${API_URL}/auth/login`, { email, password });
    
    // Return the response data (user data or token, depending on backend response)
    return response.data;
  } catch (error) {
    console.error("Login error: ", error);
    throw error;  // Rethrow error to handle it in the LoginPage component
  }
};

// You can add more functions here for other API calls (e.g., registerUser, getProfile, etc.)
