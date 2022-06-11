const baseUrl = 'http://localhost:8080';

const service = {

  buildInit: function (method, headers, body) {
    const init = {
      method,
      headers: {
        ...headers,
      },
    };
    if (body) init.body = JSON.stringify(body);
    return init;
  },

  index: async function (path) {
    const init = this.buildInit('GET', { Accept: 'application/json' });

    const response = await fetch(`${baseUrl}/${path}`, init);
    if (response.status === 200) {
      return response.json();
    }
    return Promise.reject('Could not fetch games.');
  },

  update: async function (path, payload) {
    const init = this.buildInit(
      'PUT',
      { 'Content-Type': 'application/json' },
      payload
    );
    const response = await fetch(`${baseUrl}/${path}`, init);

    if (response.status >= 400) {
      return Promise.reject(`Unable to update!`);
    }

    return true;
  },

  delete: async function (path, id) {
    const response = await fetch(
      `${baseUrl}/${path}/${id}`,
      this.buildInit('DELETE', {})
    );

    if (response.status >= 400) {
      return Promise.reject(`Unable to delete 🔥.`);
    }

    return true;
  },

  create: async function (path, payload) {
    const init = this.buildInit(
      'POST',
      {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      payload
    );

    const response = await fetch(`${baseUrl}/${path}`, init);

    if (response.status >= 400) {
      return Promise.reject(`Unable to create.`);
    }

    return response.json();
  },
};

export default service;
