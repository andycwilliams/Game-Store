import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

function ConsoleForm({ handler }) {
  return (
    <Form onSubmit={handler}>
      <Form.Group>
        <Form.Label htmlFor="model">Model</Form.Label>
        <Form.Control type="text" name="model" id="model" />
      </Form.Group>

      <Form.Group>
        <Form.Label htmlFor="manufacturer">Manufacturer</Form.Label>
        <Form.Control type="text" name="manufacturer" id="manufacturer" />
      </Form.Group>

      <Form.Group>
        <Form.Label htmlFor="memoryAmount">Memory</Form.Label>
        <Form.Control type="text" name="memoryAmount" id="memoryAmount" />
      </Form.Group>

      <Form.Group>
        <Form.Label htmlFor="processor">Processor</Form.Label>
        <Form.Control type="text" name="processor" id="processor" />
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
        Add Console
      </Button>
    </Form>
  );
}

export default ConsoleForm;
