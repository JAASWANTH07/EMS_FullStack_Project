import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth-service';
import { inject } from '@angular/core';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService: AuthService = inject(AuthService);
  if (
    !req.url.endsWith('/auth/login') &&
    !req.url.endsWith('/auth/register') 
  ) {
    let token = authService.retrieveToken();

    if (token) {
      let authReq = req.clone({
        setHeaders: { Authorization: `Bearer ${token}` },
      });
      return next(authReq);
    }

    return next(req);
  }

  return next(req);
};


