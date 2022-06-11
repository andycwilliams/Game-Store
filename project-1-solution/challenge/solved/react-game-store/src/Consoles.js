import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Table from 'react-bootstrap/Table';
import ConsoleForm from './ConsoleForm';
import service from './service';

function Consoles({ handler }) {
  const [consoles, setConsoles] = useState([]);
  const [editedConsole, setEditedConsole] = useState({});
  const [error, setError] = useState('');
  const [manufacturerFilters, setManufacturerFilters] = useState([]);

  useEffect(() => {
    service
      .index('console')
      .then(results => {
        setConsoles(results);
        setManufacturerFilters(() => {
          const uniqueManufacturers = [
            ...new Set(results.map(({ manufacturer }) => manufacturer)),
          ];

          // Create an array of search filters to be turned into checkboxes
          return uniqueManufacturers.map(manufacturer => ({
            manufacturer,
            isChecked: false,
          }));
        });
      })
      .catch(err => setError(err.toString()));
  }, []);

  const handleBlur = () => {
    service.update('console', editedConsole).catch(err => {
      setError(err.toString());
    });
  };

  const handleConsoleChange = ({ target }) => {
    const updatedConsole = {
      ...consoles.find(
        ({ id }) =>
          // ⚠️ Be sure to compare as numbers!
          id === Number(target.closest('tr').dataset.id)
      ),
      ...{ [target.dataset.key]: target.value },
    };

    setEditedConsole(updatedConsole);
    setConsoles(prevConsoles => {
      return (
        prevConsoles
          .filter(({ id }) => id !== updatedConsole.id)
          .concat(updatedConsole)

          // Keep order of ids after concatenation
          .sort((a, b) => a.id - b.id)
      );
    });
  };

  const handleDelete = ({ target }) => {
    const targetId = Number(target.closest('tr').dataset.id);
    service
      .delete('console', targetId)
      .then(() => {
        setConsoles(prev => prev.filter(({ id }) => id !== targetId));
      })
      .catch(err => {
        setError(err.toString());
      });
  };

  const handleOrderPlacement = event => {
    event.preventDefault();
    const orderedConsole = consoles.find(
      ({ id }) => id === Number(event.target.closest('tr').dataset.id)
    );

    // Have to specify this to differentiate from other types of items in order
    orderedConsole.orderId = `C-${orderedConsole.id}`;

    if (
      event.target.elements[0].value > 0 &&
      event.target.elements[0].value <= orderedConsole.quantity
    ) {
      handler({
        ...orderedConsole,
        ...{ numOrdered: Number(event.target.elements[0].value) },
      });

      event.target.reset();
    } else {
      setError('Invalid quantity!');
    }
  };

  const handleManufacturerFilter = event => {
    setManufacturerFilters(prev => {
      const ret = [...prev];
      const toggledFilter = ret[event.target.dataset.index];
      toggledFilter.isChecked = event.target.checked;

      return ret;
    });
  };

  const handleSubmit = event => {
    event.preventDefault();
    service
      .create('console', Object.fromEntries(new FormData(event.target)))
      .then(results => {
        event.target.reset();
        setConsoles(prev => prev.concat(results));
      })
      .catch(err => {
        setError(err.toString());
      });
  };

  const resetError = () => {
    setError('');
  };

  const activeManufacturerFilters = manufacturerFilters
    .filter(({ isChecked }) => isChecked)
    .map(({ manufacturer }) => manufacturer);

  const filteredConsoles = consoles.filter(({ manufacturer }) =>
    activeManufacturerFilters.length
      ? activeManufacturerFilters.includes(manufacturer)
      : true
  );

  return (
    <main>
      <section>
        <h2>Filter by Manufacturer</h2>
        {manufacturerFilters.map(({ manufacturer, checked }, index) => (
          <Form.Check
            type="checkbox"
            label={manufacturer}
            id={manufacturer.split(' ')[0].toLowerCase()}
            checked={checked}
            inline
            data-index={index}
            onChange={handleManufacturerFilter}
            key={index}
          />
        ))}
      </section>
      {error && <p className="alert alert-danger">{error}</p>}
      <Table striped bordered hover size="sm" className="mt-4">
        <thead>
          <tr>
            <th>Model</th>
            <th>Manufacturer</th>
            <th>Memory 🧠</th>
            <th>Processor 💻</th>
            <th>Price</th>
            <th>Qty</th>
          </tr>
        </thead>
        <tbody>
          {filteredConsoles.map(
            ({
              id,
              model,
              manufacturer,
              memoryAmount,
              price,
              quantity,
              processor,
            }) => (
              <tr key={id} data-id={id}>
                <td>
                  <Form.Control
                    type="text"
                    value={model}
                    onChange={handleConsoleChange}
                    onBlur={handleBlur}
                    data-key="model"
                  />
                </td>

                <td>
                  <Form.Control
                    type="text"
                    value={manufacturer}
                    onChange={handleConsoleChange}
                    onBlur={handleBlur}
                    data-key="manufacturer"
                  />
                </td>

                <td>
                  <Form.Control
                    type="text"
                    value={memoryAmount}
                    onChange={handleConsoleChange}
                    onBlur={handleBlur}
                    data-key="memoryAmount"
                  />
                </td>

                <td>
                  <Form.Control
                    type="text"
                    value={processor}
                    onChange={handleConsoleChange}
                    onBlur={handleBlur}
                    data-key="processor"
                  />
                </td>

                <td>
                  <Form.Control
                    type="number"
                    value={price}
                    onChange={handleConsoleChange}
                    onBlur={handleBlur}
                    data-key="price"
                    step="0.01"
                  />
                </td>

                <td>
                  <Form.Control
                    type="number"
                    value={quantity}
                    onChange={handleConsoleChange}
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
          <h2>Add a Console</h2>
          <ConsoleForm handler={handleSubmit} />
        </section>
    </main>
  );
}

export default Consoles;
