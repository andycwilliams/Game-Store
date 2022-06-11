import Form from 'react-bootstrap/Form';
import FormControl from 'react-bootstrap/FormControl';

function SearchForm({ handler }) {
  return (
    <Form.Group className="mt-4">
      <Form.Label className="sr-only">Search Games</Form.Label>
      <FormControl
        type="search"
        placeholder="Search 🔍"
        className="mr-sm-2 w-25"
        onKeyUp={handler}
      />
    </Form.Group>
  );
}

export default SearchForm;
