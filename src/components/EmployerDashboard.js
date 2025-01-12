import React, { useEffect, useState } from "react";
import { Button, Container, Row, Col, ListGroup } from "react-bootstrap";
import axios from "axios";
import StarRatings from "react-star-ratings";

const EmployerDashboard = () => {
  const [jobSeekers, setJobSeekers] = useState([]);
  const [rating, setRating] = useState(0); // State for job seeker rating

  // Check if the user is authenticated (e.g., token exists)
  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      window.location.href = "/login"; // Redirect to login if no token is found
    } else {
      // Fetch available job seekers (mocked for now, could be real API call)
      fetchJobSeekers(token);  // Call the API with token
    }
  }, []);

  const fetchJobSeekers = async (token) => {
    try {
      const response = await axios.get("http://localhost:9091/api/employer/job-seekers", {
        headers: {
          Authorization: `Bearer ${token}`, // Send token as Bearer token
        },
      });
      setJobSeekers(response.data);  // Assuming response contains job seekers
    } catch (err) {
      console.error("Error fetching job seekers:", err);
    }
  };

  const handleRatingChange = (newRating) => {
    setRating(newRating);
  };

  const submitRating = () => {
    console.log("Rating submitted successfully:", rating);
  };

  return (
    <Container>
      <h2>Employer Dashboard</h2>
      <Row>
        <Col md={6}>
          <h4>Available Job Seekers</h4>
          <ListGroup>
            {jobSeekers.map((jobSeeker, index) => (
              <ListGroup.Item key={index}>
                {jobSeeker.name} - {jobSeeker.skills.join(", ")}
              </ListGroup.Item>
            ))}
          </ListGroup>
        </Col>
        <Col md={6}>
          <h4>Rate Job Seekers</h4>
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
        </Col>
      </Row>
      <Button variant="primary">Post a New Job</Button>
    </Container>
  );
};

export default EmployerDashboard;
