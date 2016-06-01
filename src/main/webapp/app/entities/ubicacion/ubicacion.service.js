(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Ubicacion', Ubicacion);

    Ubicacion.$inject = ['$resource'];

    function Ubicacion ($resource) {
        var resourceUrl =  'api/ubicacions/:id';

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
        })
    }
})();
