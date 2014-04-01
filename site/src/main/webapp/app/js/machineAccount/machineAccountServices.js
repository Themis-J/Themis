'use strict';

angular.module('machineAccount.services', ['ngResource', 'ngCookies'])
    .factory('machineAccountService', ['$resource', 'config', function ($resource, config) {
        return $resource(config.service.url + '/dealer/:path', {}, {
            getLedgerMetadata: {method: 'GET', params: {path: 'ledgerMetadata'}, isArray: false},
            saveVehicleSalesLedgerData: {method: 'POST', params: {path: 'vehicleSalesLedger'}, isArray: false},
            getVehicleSalesLedgerData: {method: 'GET', params: {path: 'vehicleSalesLedger'}, isArray: false},
            queryVehicleSalesLedgerData: {method: 'GET', params: {path: 'vehicleSalesLedgers', contractNo: null,
                model: null, type: null, color: null, lpNumber: null, frameNo: null, manufacturerDebitDate: null, warehousingDate: null,
                salesDate: null, guidingPrice: null, customerName: null, identificationNo: null, salesConsultant: null, customerType: null}, isArray: false},
            savePostSalesLedgerData: {method: 'POST', params: {path: 'postSalesLedger'}, isArray: false},
            getPostSalesLedgerData: {method: 'GET', params: {path: 'postSalesLedger'}, isArray: false},
            queryPostSalesLedgerData: {method: 'GET', params: {path: 'postSalesLedgers', workOrderNo: null, salesDate: null,
                mileage: null, lpNumber: null, customerName: null, color: null, frameNo: null, model: null, enterFactoryDate: null, exitFactoryDate: null,
                customerType: null, insuranceAgengcy: null, insuranceDueDate: null, insuranceClaimNumber: null}, isArray: false}
        });
    }])