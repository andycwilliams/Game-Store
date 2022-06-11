import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Table from 'react-bootstrap/Table';
import service from './service';

function FinalizeOrder({ items, handler, qtyHandler }) {
  const [points, setPoints] = useState(null);
  const [error, setError] = useState('');

  const handleClick = () => {
    setError('');
    const inventoryUpdates = items.map(item => {
      return { ...item, ...{ quantity: item.quantity - item.numOrdered } };
    });

    Promise.all(
      inventoryUpdates.map(inventoryUpdate => {
        // Server appears to strip out `orderId` 😌
        if (inventoryUpdate.orderId.startsWith('G')) {
          return service.update('game', inventoryUpdate);
        }

        if (inventoryUpdate.orderId.startsWith('S')) {
          return service.update('tshirt', inventoryUpdate);
        }

        if (inventoryUpdate.orderId.startsWith('C')) {
          return service.update('console', inventoryUpdate);
        }
      })
    )
      .then(() => {
        setPoints(() =>
          items.reduce((points, item) => {
            points += Number(item.numOrdered);
            return points;
          }, 0)
        );
        handler();
      })
      .catch(err => {
        setError(`${err} - Probably trying to order too many`);
      });
  };

  return (
    <>
      <Table striped bordered hover>
        <caption>Your Order</caption>
        <tbody>
          {items.map(item => (
            <tr key={item.orderId} data-order-id={item.orderId}>
              <td>
                {item.title ||
                  item.model ||
                  `${item.size} - ${item.color} - ${item.description}`}
              </td>
              <td>
                <Form.Control
                  type="number"
                  onBlur={qtyHandler}
                  onChange={qtyHandler}
                  data-key="quantity"
                  value={item.numOrdered}
                />
                {}
              </td>
              <td>${item.price}</td>
              <td>{(item.numOrdered * item.price).toFixed(2)}</td>
            </tr>
          ))}
        </tbody>
        <tfoot>
          <tr>
            <td>Total</td>
            <td>
              $
              {items
                .reduce((total, item) => {
                  total += item.price * item.numOrdered;
                  return total;
                }, 0)
                .toFixed(2)}
            </td>
          </tr>
        </tfoot>
      </Table>
      <Button
        variant="success"
        size="lg"
        onClick={handleClick}
        disabled={items.length === 0}
      >
        Do it!
      </Button>
      {points ? (
        <p className="fs-2 fw-bold text-success">{`🍄 You just got ${points} Level Up points!`}</p>
      ) : null}
      {error ? <p className="fs-2 fw-bold text-danger">{error}</p> : null}
    </>
  );
}

export default FinalizeOrder;
