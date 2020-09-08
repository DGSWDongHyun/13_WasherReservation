const api = require('express').Router();
const WasherCtrl = require('./washerCtrl');

api.use('/washerCtrl', WasherCtrl);

module.exports = api;