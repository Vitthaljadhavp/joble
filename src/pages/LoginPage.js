import React, { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { loginUser } from '../api';  // Assuming you have an api.js file to manage backend requests

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  // Handle form submission
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      // Attempt to log in the user
      const userData = await loginUser(email, password);
      
      // Destructure response data
      const { token, role } = userData;

      // Handle successful login (e.g., store JWT token in local storage)
      localStorage.setItem('authToken', token);  // Store JWT token
      localStorage.setItem('role', role);  // Store user role (employer/jobseeker)
      
    //   // Navigate to home page or dashboard
    //   navigate('/dashboard');
    // } catch (err) {
    //   // Handle error (e.g., show error message)
    //   setError(err.response ? err.response.data : 'An error occurred. Please try again.');
    // }

    // Navigate based on the user role (for example)
      if (role === 'EMPLOYER') {
        navigate('/employer-dashboard');  // Employer Dashboard
      } else if (role === 'JOBSEEKER') {
        navigate('/jobseeker-dashboard');  // Jobseeker Dashboard
      } else {
        navigate('/dashboard');  // Fallback to general dashboard if role is not recognized
      }
    } catch (err) {
      // Handle error (show message or log)
      setError(err.response ? err.response.data : 'An error occurred. Please try again.');
    }
  };

  return (
    <Container className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
      <Form style={{ width: '300px' }} onSubmit={handleLogin}>
        <h2 className="text-center mb-4">Login</h2>
        {error && <div className="alert alert-danger">{error}</div>} {/* Display error message */}
        <Form.Group controlId="email">
          <Form.Label>Email address</Form.Label>
          <Form.Control
            type="email"
            placeholder="Enter email"
            value={email}
            onChange={(e) => setEmail(e.target.value)} // Update email state
            required
          />
        </Form.Group>
        <Form.Group controlId="password" className="mt-3">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            placeholder="Enter password"
            value={password}
            onChange={(e) => setPassword(e.target.value)} // Update password state
            required
          />
        </Form.Group>
        <Button variant="primary" type="submit" className="mt-4 w-100">
          Login
        </Button>
      </Form>
    </Container>
  );
};

export default LoginPage;
