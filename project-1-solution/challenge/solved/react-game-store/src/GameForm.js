import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

function GameForm({ handler }) {
  return (
    <Form onSubmit={handler}>
      <Form.Group>
        <Form.Label htmlFor="title">Title</Form.Label>
        <Form.Control
          type="text"
          placeholder="Game Title"
          name="title"
          id="title"
        />
      </Form.Group>

      <Form.Group>
        <Form.Label htmlFor="esrbRating">ESRB Rating</Form.Label>
        <Form.Control type="text" name="esrbRating" id="esrbRating" />
      </Form.Group>

      <Form.Group>
        <Form.Label htmlFor="description">Description</Form.Label>
        <Form.Control type="text" name="description" id="description" />
      </Form.Group>

      <Form.Group>
        <Form.Label htmlFor="price">Price</Form.Label>
        <Form.Control type="number" name="price" step="0.01" id="price" />
      </Form.Group>

      <Form.Group>
        <Form.Label htmlFor="quantity">Quantity</Form.Label>
        <Form.Control type="number" name="quantity" id="quantity" />
      </Form.Group>

      <Form.Group>
        <Form.Label htmlFor="studio">Studio</Form.Label>
        <Form.Control type="text" name="studio" id="studio" />
      </Form.Group>

      <Button variant="primary" type="submit">
        Add Game
      </Button>
    </Form>
  );
}

export default GameForm;
