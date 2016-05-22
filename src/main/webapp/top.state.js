(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('Top10', {
            parent: 'app',
            url: '/topcervesas',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'Top10.html',
                    controller: 'CtrlTop',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
