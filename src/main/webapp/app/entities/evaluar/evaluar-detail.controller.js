(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('EvaluarDetailController', EvaluarDetailController);

    EvaluarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Evaluar', 'Cervesa', 'User'];

    function EvaluarDetailController($scope, $rootScope, $stateParams, entity, Evaluar, Cervesa, User) {
        var vm = this;
        vm.evaluar = entity;
        vm.load = function (id) {
            Evaluar.get({id: id}, function(result) {
                vm.evaluar = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterApp:evaluarUpdate', function(event, result) {
            vm.evaluar = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
