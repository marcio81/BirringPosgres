(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('CtrlTop', TOPController);

    TOPController.$inject = ['$scope', 'Principal', 'LoginService', 'Cervesa',];

    function TOPController ($scope, Principal, Cervesa, LoginService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
            getTopCervezas();
        });

        getAccount();
        getTopCervezas();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        //HAY QUE AÑADIR EN LA PARTE SUPERIOR CERVESA
        function getTopCervezas() {
            Cervesa.consultaTop( function (response){
            //$http.get("api/cervesas").then(function (response) {
                $scope.cervesas = response;
        })
    }}});

