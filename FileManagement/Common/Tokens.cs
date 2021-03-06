﻿using System;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

using FileManagement.Auth.Contracts;
using FileManagement.Models;

using Newtonsoft.Json;

namespace FileManagement.Common
{
    public static class Tokens
    {
        public static async Task<string> GenerateJwt
        (
            ClaimsIdentity identity,
            IJwtFactory jwtFactory,
            string username,
            Guid folder,
            JwtIssuerOptions jwtOptions,
            JsonSerializerSettings serializerSettings
        )
        {
            var response = new
            {
                id = identity.Claims.Single(c => c.Type == GlobalConstants.Id).Value,
                auth_token = await jwtFactory.GenerateEncodedToken(username, identity),
                expires_in = (int) jwtOptions.ValidFor.TotalMinutes,
                user_folder = folder.ToString()
            };

            return JsonConvert.SerializeObject(response, serializerSettings);
        }
    }
}
