import React, { useEffect, useState } from "react";
import { Button, Container, Row, Col, ListGroup } from "react-bootstrap";
import axios from "axios";
import StarRatings from "react-star-ratings";

const JobSeekerDashboard = () => {
  const [available, setAvailable] = useState(false); // To track availability status
  const [jobApplications, setJobApplications] = useState([]);
  const [rating, setRating] = useState(0); // State for job seeker rating

  // Check if the user is authenticated (e.g., token exists)
  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      window.location.href = "/login"; // Redirect to login if no token is found
    } else {
      // Fetch job applications (mocked for now, could be real API call)
      fetchJobApplications(token);  // Call the API with token
    }
  }, []);

  const fetchJobApplications = async (token) => {
    try {
      const response = await axios.get("http://localhost:9091/api/job-seeker/applications", {
        headers: {
          Authorization: `Bearer ${token}`, // Send token as Bearer token
        },
      });
      setJobApplications(response.data);  // Assuming response contains job applications
    } catch (err) {
      console.error("Error fetching job applications:", err);
    }
  };

  const toggleAvailability = () => {
    setAvailable(!available);
  };

  const handleRatingChange = (newRating) => {
    setRating(newRating);
  };

  const submitRating = () => {
    console.log("Rating submitted successfully:", rating);
  };

  return (
    <Container>
      <h2>Job Seeker Dashboard</h2>
      <Row>
        <Col md={6}>
          <Button variant="success" onClick={toggleAvailability}>
            {available ? "Mark as Unavailable" : "Mark as Available"}
          </Button>
          <p>{available ? "You are available for work" : "You are not available for work"}</p>
        </Col>
        <Col md={6}>
          <h4>Your Job Applications</h4>
          <ListGroup>
            {jobApplications.map((application, index) => (
              <ListGroup.Item key={index}>
                {application.jobTitle} - {application.status}
              </ListGroup.Item>
            ))}
          </ListGroup>
        </Col>
      </Row>
      <h4>Rate Employers</h4>
      <StarRatings
        rating={rating}
        starRatedColor="gold"
        changeRating={handleRatingChange}
        numberOfStars={5}
        name="rating"
      />
      <Button variant="primary" onClick={submitRating}>
        Submit Rating
      </Button>
    </Container>
  );
};

export default JobSeekerDashboard;
