import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

function ShirtForm({ handler }) {
  return (
    <Form onSubmit={handler}>
      <Form.Group>
        <Form.Label htmlFor="size">Size</Form.Label>
        <Form.Control type="text" name="size" id="size" />
      </Form.Group>

      <Form.Group>
        <Form.Label htmlFor="color">Color</Form.Label>
        <Form.Control type="text" name="color" id="size" />
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

      <Button variant="primary" type="submit">
        Add Shirt
      </Button>
    </Form>
  );
}

export default ShirtForm;
