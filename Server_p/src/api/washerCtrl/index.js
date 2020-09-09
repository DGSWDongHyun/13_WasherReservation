const api = require('express').Router();
const WasherCtrl = require('./washer.ctrl');

api.post('/washerRegister', WasherCtrl.washerRegister);
api.post('/deleteWashers', WasherCtrl.deleteWashers);
api.get('/getWashers', WasherCtrl.getWashers);

module.exports = api;