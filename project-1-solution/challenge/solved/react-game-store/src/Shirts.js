import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Table from 'react-bootstrap/Table';
import service from './service';
import ShirtForm from './ShirtForm';

function Shirts({ handler }) {
  const [shirts, setShirts] = useState([]);
  const [editedShirt, setEditedShirt] = useState({});
  const [error, setError] = useState('');
  const [sizeFilters, setSizeFilters] = useState([]);
  const [colorFilters, setColorFilters] = useState([]);

  useEffect(() => {
    service
      .index('tshirt')
      .then(results => {
        setShirts(results);

        setSizeFilters(() => {
          const uniqueSizes = [...new Set(results.map(({ size }) => size))];

          // Create an array of search filters to be turned into checkboxes
          return uniqueSizes.map(size => ({
            size,
            isChecked: false,
          }));
        });

        setColorFilters(() => {
          const uniqueColors = [...new Set(results.map(({ color }) => color))];

          // Create an array of search filters to be turned into checkboxes
          return uniqueColors.map(color => ({
            color,
            isChecked: false,
          }));
        });
      })
      .catch(err => setError(err.toString()));
  }, []);

  const handleBlur = () => {
      service.update('tshirt', editedShirt).catch(err => {
        setError(err.toString());
      });
  };

  const handleChange = ({ target }) => {
    const updatedShirt = {
      ...shirts.find(
        shirt =>
          // ⚠️ Be sure to compare as numbers!
          shirt.id === Number(target.closest('tr').dataset.id)
      ),
      ...{ [target.dataset.key]: target.value },
    };

    setEditedShirt(updatedShirt);
    setShirts(prevShirts => {
      return (
        prevShirts
          .filter(({ id }) => id !== updatedShirt.id)
          .concat(updatedShirt)

          // Keep order of ids after concatenation
          .sort((a, b) => a.id - b.id)
      );
    });
  };

  const handleDelete = ({ target }) => {
    const targetId = Number(target.closest('tr').dataset.id);
    service
      .delete('tshirt', targetId)
      .then(() => {
        setShirts(prev => prev.filter(({ id }) => id !== targetId));
      })
      .catch(err => {
        setError(err.toString());
      });
  };

  const handleOrderPlacement = event => {
    event.preventDefault();
    const orderedShirt = shirts.find(
      ({ id }) => id === Number(event.target.closest('tr').dataset.id)
    );

    // Have to specify this to differentiate from other types of items in order
    orderedShirt.orderId = `S-${orderedShirt.id}`;

    if (
      event.target.elements[0].value > 0 &&
      event.target.elements[0].value <= orderedShirt.quantity
    ) {
      handler({
        ...orderedShirt,
        ...{ numOrdered: Number(event.target.elements[0].value) },
      });

      event.target.reset();
    } else {
      setError('Invalid quantity!');
    }
  };

  const handleSubmit = event => {
    event.preventDefault();

    service
      .create('tshirt', Object.fromEntries(new FormData(event.target)))
      .then(results => {
        event.target.reset();
        setShirts(prev => prev.concat(results));
      })
      .catch(err => {
        setError(err.toString());
      });
  };

  const handleSizeFilter = event => {
    setSizeFilters(prev => {
      // Avoid mutation
      const ret = [...prev];
      const toggledFilter = ret[event.target.dataset.index];
      toggledFilter.isChecked = event.target.checked;

      return ret;
    });
  };

  const handleColorFilter = event => {
    setColorFilters(prev => {
      // Avoid mutation
      const ret = [...prev];
      const toggledFilter = ret[event.target.dataset.index];
      toggledFilter.isChecked = event.target.checked;

      return ret;
    });
  };

  const resetError = () => {
    setError('');
  };

  const activeSizeFilters = sizeFilters
    .filter(({ isChecked }) => isChecked)
    .map(({ size }) => size);

  const activeColorFilters = colorFilters
    .filter(({ isChecked }) => isChecked)
    .map(({ color }) => color);

  const filteredShirts = shirts
    .filter(({ size }) =>
      activeSizeFilters.length ? activeSizeFilters.includes(size) : true
    )
    .filter(({ color }) =>
      activeColorFilters.length ? activeColorFilters.includes(color) : true
    );

  return (
    <main>
      {error && <p className="alert alert-danger">{error}</p>}

      <Row>
        <Col>
          <section>
            <h2>Filter by Size</h2>
            {sizeFilters.map(({ size, checked }, index) => (
              <Form.Check
                type="checkbox"
                label={size}
                id={size.split(' ')[0].toLowerCase()}
                checked={checked}
                inline
                data-index={index}
                onChange={handleSizeFilter}
                key={index}
              />
            ))}
          </section>
        </Col>

        <Col>
          <section>
            <h2>Filter by Color</h2>
            {colorFilters.map(({ color, isChecked }, index) => (
              <Form.Check
                type="checkbox"
                label={color}
                id={color.toLowerCase()}
                checked={isChecked}
                inline
                onChange={handleColorFilter}
                data-index={index}
                key={index}
              />
            ))}
          </section>
        </Col>
      </Row>

      <Table striped bordered hover size="sm">
        <thead>
          <tr>
            <th>Size</th>
            <th>Color</th>
            <th>Description</th>
            <th>Price</th>
            <th>Qty</th>
          </tr>
        </thead>
        <tbody>
          {filteredShirts.map(
            ({ id, size, color, description, price, quantity, studio }) => (
              <tr key={id} data-id={id}>
                <td>
                  <Form.Control
                    type="text"
                    value={size}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    data-key="size"
                  />
                </td>

                <td>
                  <Form.Control
                    type="text"
                    value={color}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    data-key="color"
                  />
                </td>

                <td>
                  <Form.Control
                    type="text"
                    value={description}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    data-key="description"
                  />
                </td>

                <td>
                  <Form.Control
                    type="number"
                    value={price}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    data-key="price"
                    step="0.01"
                  />
                </td>

                <td>
                  <Form.Control
                    type="number"
                    value={quantity}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    data-key="quantity"
                  />
                </td>

                <td>
                  <Form onSubmit={handleOrderPlacement} className="d-flex">
                    <Form.Control
                      type="number"
                      size="sm"
                      onChange={resetError}
                    />
                    <Button
                      variant="success"
                      size="sm"
                      disabled={quantity === 0}
                      className="ml-1"
                      type="submit"
                    >
                      ➕
                    </Button>
                  </Form>
                </td>

                <td>
                  <Button variant="danger" size="sm" onClick={handleDelete}>
                    Delete
                  </Button>
                </td>
              </tr>
            )
          )}
        </tbody>
      </Table>
        <section>
          <h2>Add a Shirt</h2>
          <ShirtForm handler={handleSubmit} />
        </section>
    </main>
  );
}

export default Shirts;
