'use strict';

angular.module('namaaApaApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
