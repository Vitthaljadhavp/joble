import React from 'react';
import { Container, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';  // Import Link from react-router-dom

const HomePage = () => {
  return (
    <Container className="mt-5">
      <div className="p-5 text-center bg-light rounded-3">
        <h1>Welcome to Joble</h1>
        <p className="lead">Find your dream job or hire the perfect candidate!</p>
        <Link to="/login">  {/* Use Link to navigate to login page */}
          <Button variant="primary" className="me-2">Login</Button>
        </Link>
        <Link to="/register">  {/* Use Link to navigate to register page */}
          <Button variant="secondary">Register</Button>
        </Link>
      </div>
    </Container>
  );
};

export default HomePage;
