import React, { useEffect } from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import './styles.css';


const LandingPage: React.FC = () => {

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const token = params.get('token');
    const error = params.get('error');

    if (token) {
      console.log('OAuth token:', token);
      params.delete('token');
      window.history.replaceState({}, document.title, window.location.pathname);
    } else if (error) {
      params.delete('error');
      window.history.replaceState({}, document.title, window.location.pathname);
    }
  }, []);


  const handleGoogleLogin = () => {
    window.location.href = 'https://backend.com/auth/google';
  };

  const handleGithubLogin = () => {
    window.location.href = 'https://backend.com/auth/github';
  };

  return (
    <div className="app-container gradient-background">
      <div className="main-content">
        <Container fluid>
          <Row className="align-items-center">
            <Col md={8} className="text-section">
              <h1 className="heading">Plan Your College Journey</h1>
              <p style={{ fontSize: '1.6rem' }}>
                Our innovative tool helps you project your entire college experience.
                By inputting your courses, semesters, and prerequisites, you’ll receive
                a custom, ideal schedule tailored just for you.
              </p>
              <ul style={{ fontSize: '1.4rem' }}>
                <li><strong>Customizable Schedules:</strong> Adapt your plan as your interests change.</li>
                <li><strong>Visual Timelines:</strong> See your semester-by-semester progress.</li>
                <li><strong>Prerequisite Management:</strong> Never miss an important course requirement.</li>
              </ul>
            </Col>

            <Col md={4} className="d-flex justify-content-center align-items-center">
              <Card className="login-card">
                <Card.Body className="card-body">
                  <Card.Title style={{ textAlign: 'center', color: '#DC3D24', fontSize: '3rem' }}>
                    Login
                  </Card.Title>
                  <div className="d-grid gap-3 mt-4">
                    <Button
                      className="custom-button"
                      onClick={handleGoogleLogin}
                      style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
                    >
                      <img
                        src="/google.png"
                        alt="Google"
                        style={{ width: '20px', marginRight: '10px' }}
                      />
                      Continue with Google
                    </Button>
                    <Button
                      className="custom-button"
                      onClick={handleGithubLogin}
                      style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
                    >
                      <img
                        src="/github.png"
                        alt="GitHub"
                        style={{ width: '20px', marginRight: '10px' }}
                      />
                      Continue with GitHub
                    </Button>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </Container>
      </div>

      <footer className="app-footer">
        <p style={{ margin: 0 }}>
          Kamil Woskowiak&nbsp;&nbsp;|&nbsp;&nbsp;
          <a
            href="https://github.com/KamilWoskowiak"
            target="_blank"
            rel="noopener noreferrer"
          >
            GitHub
          </a>
        </p>
      </footer>

      <div
        style={{
          position: 'fixed',
          bottom: '20px',
          right: '20px',
          zIndex: 1000,
          width: '300px',
        }}
      >
      </div>
    </div>
  );
};

export default LandingPage;
