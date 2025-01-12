import React, { useState, useEffect } from "react";
import { Redirect, useHistory } from "react-router-dom"; // Use useHistory for redirecting in React Router v5

import EmployerDashboard from "./EmployerDashboard";
import JobSeekerDashboard from "./JobSeekerDashboard";
import { validateToken } from "../api"; // You should create a function to validate the token with the backend

const Dashboard = () => {
  const [role, setRole] = useState(""); // This will hold the user's role (employer or jobseeker)
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const history = useHistory();  // useHistory hook for programmatic navigation

  // Fetch user role and validate the token
  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      history.push("/login");  // Redirect to login if token is missing
      return;
    }

    // Send token to backend for validation (optional, if needed)
    const fetchUserData = async () => {
      try {
        // Replace with your backend endpoint to validate the token
        const response = await validateToken(token); 

        if (response.status === 200) {
          const userRole = localStorage.getItem("role");  // You may store the role during login
          setRole(userRole);
          setIsAuthenticated(true);
        } else {
          // Token is invalid, redirect to login
          history.push("/login");
        }
      } catch (error) {
        console.error("Token validation failed:", error);
        history.push("/login");  // Redirect to login on failure
      }
    };

    fetchUserData();
  }, [history]);

  // If the user is not authenticated, return redirect (if role isn't found or validated)
  if (!isAuthenticated || !role) {
    return <Redirect to="/login" />;
  }

  // Render the dashboard based on the role
  return (
    <div>
      {role === "employer" ? <EmployerDashboard /> : <JobSeekerDashboard />}
    </div>
  );
};

export default Dashboard;
