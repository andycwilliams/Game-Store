import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Table from 'react-bootstrap/Table';
import GameForm from './GameForm';
import Search from './SearchForm';
import service from './service';

function Games({ handler }) {
  const [games, setGames] = useState([]);
  const [editedGame, setEditedGame] = useState({});
  const [searchTerm, setSearchTerm] = useState('');
  const [error, setError] = useState('');
  const [studioFilters, setStudioFilters] = useState([]);
  const [esrbFilters, setEsrbFilters] = useState([]);

  useEffect(() => {
    service
      .index('game')
      .then(results => {
        setGames(() => results);

        setStudioFilters(() => {
          const uniqueStudios = [
            ...new Set(results.map(({ studio }) => studio)),
          ];

          // Create an array of search filters to be turned into checkboxes
          return uniqueStudios.map(studio => ({
            studio,
            isChecked: false,
          }));
        });

        setEsrbFilters(() => {
          const uniqueEsrbs = [
            ...new Set(results.map(({ esrbRating }) => esrbRating)),
          ];

          // Create an array of search filters to be turned into checkboxes
          return uniqueEsrbs.map(esrbRating => ({
            esrbRating,
            isChecked: false,
          }));
        });
      })
      .catch(err => setError(err.toString()));
  }, []);

  const handleBlur = () => {
    service.update('game', editedGame).catch(err => {
      setError(err.toString());
    });
  };

  const handleGameChange = ({ target }) => {
    const updatedGame = {
      ...games.find(
        game =>
          // ⚠️ Be sure to compare as numbers!
          game.id === Number(target.closest('tr').dataset.id)
      ),
      ...{ [target.dataset.key]: target.value },
    };

    setEditedGame(updatedGame);
    setGames(prevGames => {
      return (
        prevGames
          .filter(({ id }) => id !== updatedGame.id)
          .concat(updatedGame)

          // Keep order of ids after concatenation
          .sort((a, b) => a.id - b.id)
      );
    });
  };

  // Only admin sees `delete`
  const handleDelete = ({ target }) => {
    const targetId = Number(target.closest('tr').dataset.id);
    service
      .delete('game', targetId)
      .then(() => {
        setGames(prev => prev.filter(({ id }) => id !== targetId));
      })
      .catch(err => {
        setError(err.toString());
      });
  };

  const handleOrderPlacement = event => {
    event.preventDefault();
    const orderedGame = games.find(
      ({ id }) => id === Number(event.target.closest('tr').dataset.id)
    );

    // Have to specify this to differentiate from other types of items in order
    orderedGame.orderId = `G-${orderedGame.id}`;

    if (
      event.target.elements[0].value > 0 &&
      event.target.elements[0].value <= orderedGame.quantity
    ) {
      handler({
        ...orderedGame,
        ...{ numOrdered: Number(event.target.elements[0].value) },
      });

      event.target.reset();
    } else {
      setError('Invalid quantity!');
    }
  };

  const handleEsrbFilter = event => {
    setEsrbFilters(prev => {
      // Avoid mutation
      const ret = [...prev];
      const toggledFilter = ret[event.target.dataset.index];
      toggledFilter.isChecked = event.target.checked;

      return ret;
    });
  };

  const handleStudioFilter = event => {
    setStudioFilters(prev => {
      // Avoid mutation
      const ret = [...prev];
      const toggledFilter = ret[event.target.dataset.index];
      toggledFilter.isChecked = event.target.checked;

      return ret;
    });
  };

  const handleKeyUp = ({ target: { value } }) => {
    setSearchTerm(value);
  };

  const handleSubmit = event => {
    event.preventDefault();

    service
      .create('game', Object.fromEntries(new FormData(event.target)))
      .then(results => {
        event.target.reset();
        setGames(prev => prev.concat(results));
      })
      .catch(err => {
        setError(err.toString());
      });
  };

  const resetError = () => {
    setError('');
  };

  const activeEsrbFilters = esrbFilters
    .filter(({ isChecked }) => isChecked)
    .map(({ esrbRating }) => esrbRating);

  const activeStudioFilters = studioFilters
    .filter(({ isChecked }) => isChecked)
    .map(({ studio }) => studio);

  const filteredGames = games
    .filter(({ title }) =>
      title.toLowerCase().includes(searchTerm.toLowerCase())
    )
    .filter(({ esrbRating }) =>
      activeEsrbFilters.length ? activeEsrbFilters.includes(esrbRating) : true
    )
    .filter(({ studio }) =>
      activeStudioFilters.length ? activeStudioFilters.includes(studio) : true
    );

  return (
    <main>
      <Search pageName="Games" handler={handleKeyUp} />

      <Row>
        <Col>
          <section>
            <h2>Filter by Studio</h2>
            {studioFilters.map(({ studio, checked }, index) => (
              <Form.Check
                type="checkbox"
                label={studio}
                id={studio.split(' ')[0].toLowerCase()}
                checked={checked}
                inline
                data-index={index}
                onChange={handleStudioFilter}
                key={index}
              />
            ))}
          </section>
        </Col>
        <Col>
          <section>
            <h2>Filter by ESRB Rating</h2>
            {esrbFilters.map(({ esrbRating: esrb, isChecked }, index) => (
              <Form.Check
                type="checkbox"
                label={esrb}
                id={esrb.toLowerCase()}
                checked={isChecked}
                inline
                onChange={handleEsrbFilter}
                data-index={index}
                key={index}
              />
            ))}
          </section>
        </Col>
      </Row>

      {error && <p className="alert alert-danger">{error}</p>}
      <Table striped bordered hover size="sm" className="mt-4">
        <thead>
          <tr>
            <th>Title</th>
            <th>ESRB</th>
            <th>Description</th>
            <th>Price</th>
            <th>Qty</th>
            <th>Studio</th>
          </tr>
        </thead>
        <tbody>
          {filteredGames.map(
            ({
              id,
              title,
              esrbRating,
              description,
              price,
              quantity,
              studio,
            }) => (
              <tr key={id} data-id={id}>
                <td>
                  <Form.Control
                    type="text"
                    value={title}
                    onChange={handleGameChange}
                    onBlur={handleBlur}
                    data-key="title"
                  />
                </td>

                <td>
                  <Form.Control
                    type="text"
                    value={esrbRating}
                    onChange={handleGameChange}
                    onBlur={handleBlur}
                    data-key="esrbRating"
                  />
                </td>

                <td>
                  <Form.Control
                    type="text"
                    value={description}
                    onChange={handleGameChange}
                    onBlur={handleBlur}
                    data-key="description"
                  />
                </td>

                <td>
                  <Form.Control
                    type="number"
                    value={price}
                    onChange={handleGameChange}
                    onBlur={handleBlur}
                    data-key="price"
                    step="0.01"
                  />
                </td>

                <td>
                  <Form.Control
                    type="number"
                    value={quantity}
                    onChange={handleGameChange}
                    onBlur={handleBlur}
                    data-key="quantity"
                  />
                </td>

                <td>
                  <Form.Control
                    type="text"
                    value={studio}
                    onChange={handleGameChange}
                    onBlur={handleBlur}
                    data-key="studio"
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
        <h2>Add a Game</h2>
        <GameForm handler={handleSubmit} />
      </section>
    </main>
  );
}

export default Games;
