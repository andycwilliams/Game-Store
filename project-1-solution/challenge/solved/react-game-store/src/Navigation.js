import Button from 'react-bootstrap/Button';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/NavBar';
import { Link } from 'react-router-dom';

function Navigation({ items }) {
  return (
    <Navbar bg="light" expand="lg">
      <Navbar.Brand href="/">Game Store</Navbar.Brand>

      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="mr-auto">
          <Nav.Link as={Link} to="/">
            Games
          </Nav.Link>
          <Nav.Link as={Link} to="/consoles">
            Consoles
          </Nav.Link>
          <Nav.Link as={Link} to="/shirts">
            T-Shirts 👕
          </Nav.Link>

          {items?.length ? (
            <Link to="/finalize">
              <Button variant="success" type="button" className="ml-md-5">
                Place Order
              </Button>
            </Link>
          ) : null}
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
}

export default Navigation;
