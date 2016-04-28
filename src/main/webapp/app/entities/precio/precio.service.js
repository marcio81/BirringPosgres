(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Precio', Precio);

    Precio.$inject = ['$resource'];

    function Precio ($resource) {
        var resourceUrl =  'api/precios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
