import { useState } from 'react';
import Container from 'react-bootstrap/Container';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Consoles from './Consoles';
import Finalize from './FinalizeOrder';
import Games from './Games';
import Navigation from './Navigation';
import Shirts from './Shirts';

function App() {
  const [order, setOrder] = useState([]);

  function handleQtyChange(event) {
    setOrder(prev => {
      const updatedItem = {
        ...prev.find(
          ({ orderId }) =>
            orderId === event.target.closest('tr').dataset.orderId
        ),
        ...{ numOrdered: event.target.value },
      };

      return [
        ...prev.filter(
          ({ orderId }) =>
            orderId !== event.target.closest('tr').dataset.orderId
        ),
        updatedItem,
      ];
    });
  }

  function handleOrder(item) {
    // Change `numOrdered` or add item?
    setOrder(prev => {
      const item2UpdateQty = prev.find(
        ({ orderId }) => orderId === item.orderId
      );

      if (item2UpdateQty) {
        const updatedItem = {
          ...item2UpdateQty,
          ...{ numOrdered: item2UpdateQty.numOrdered + item.numOrdered },
        };

        const otherItems = prev.filter(
          ({ orderId }) => orderId !== item.orderId
        );
        return [...otherItems, updatedItem];
      }

      return [...prev, item];
    });
  }

  function finalizeOrder() {
    setOrder(() => []);
  }

  return (
    <Router>
      <Navigation items={order} />
      <Container>
        <Switch>
          <Route path="/" exact>
            <Games handler={handleOrder} />
          </Route>
          <Route path="/shirts" exact>
            <Shirts handler={handleOrder} />
          </Route>
          <Route path="/consoles" exact>
            <Consoles handler={handleOrder} />
          </Route>
          <Route path="/finalize" exact>
            <Finalize
              items={order}
              handler={finalizeOrder}
              qtyHandler={handleQtyChange}
            />
          </Route>
        </Switch>
      </Container>
    </Router>
  );
}

export default App;
