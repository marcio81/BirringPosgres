/**
 * Created by Marcio on 28/05/2016.
 */
(function () {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('CtrlBu', buscaController);

    buscaController.$inject = ['$scope', 'Principal', 'LoginService', 'Cervesa'];

    function buscaController($scope, Principal, LoginService, Cervesa) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        $scope.$on('authenticationSuccess', function () {
            getAccount();
            getTopCervezas();
        });

        getAccount();
        getTopCervezas();

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        //HAY QUE AÑADIR EN LA PARTE SUPERIOR CERVESA
        function getAllBuCervesas() {
            Cervesa.consultaBu(function (response) {
               
                vm.cervesas = response;
                //});
            });
        }
    }
});
