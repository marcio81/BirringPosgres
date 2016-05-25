(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Cervesa', Cervesa);

    Cervesa.$inject = ['$resource'];

    function Cervesa ($resource) {
        var resourceUrl =  'api/cervesas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' },
            'consultaTop': {
                method: 'GET',
                isArray: true,
                url:'api/topcervesas'
            }
        });
    }
})();
