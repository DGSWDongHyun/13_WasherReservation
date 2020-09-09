const api = require('express').Router();
const WasherCtrl = require('./washer.ctrl');

api.post('/washerRegister', WasherCtrl.washerRegister);
api.get('/getWashers', WasherCtrl.getWashers);

module.exports = api;