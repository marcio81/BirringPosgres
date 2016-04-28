(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Evaluar', Evaluar);

    Evaluar.$inject = ['$resource'];

    function Evaluar ($resource) {
        var resourceUrl =  'api/evaluars/:id';

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
