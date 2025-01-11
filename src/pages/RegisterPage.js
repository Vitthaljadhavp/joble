import React, { useState } from 'react';
import { Container, Form, Button, Alert } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';

const RegisterPage = () => {
  // State to hold form values
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  // Handle form submission
  const handleRegister = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:9091/api/users/register', {
        name,
        email,
        password,
        role,
      });

      // Handle success
      setSuccessMessage(response.data);  // Success message from backend
      setErrorMessage('');
    } catch (error) {
      // Handle error
      if (error.response) {
        setErrorMessage(error.response.data);  // Error message from backend
      } else {
        setErrorMessage('Something went wrong. Please try again later.');
      }
      setSuccessMessage('');
    }
  };

  return (
    <Container className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
      <Form style={{ width: '400px' }} onSubmit={handleRegister}>
        <h2 className="text-center mb-4">Register</h2>
        
        {/* Error and Success Messages */}
        {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
        {successMessage && <Alert variant="success">{successMessage}</Alert>}
        
        <Form.Group controlId="name">
          <Form.Label>Full Name</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter your name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group controlId="email" className="mt-3">
          <Form.Label>Email address</Form.Label>
          <Form.Control
            type="email"
            placeholder="Enter email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group controlId="password" className="mt-3">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            placeholder="Enter password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group controlId="role" className="mt-3">
          <Form.Label>Role</Form.Label>
          <Form.Select
            value={role}
            onChange={(e) => setRole(e.target.value)}
            required
          >
            <option value="">Select Role</option>
            <option value="EMPLOYER">Employer</option>
            <option value="JOB_SEEKER">Job Seeker</option>
          </Form.Select>
        </Form.Group>

        <Button variant="primary" type="submit" className="mt-4 w-100">
          Register
        </Button>
      </Form>
    </Container>
  );
};

export default RegisterPage;
