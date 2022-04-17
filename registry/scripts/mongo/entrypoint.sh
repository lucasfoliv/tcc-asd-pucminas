#!/usr/bin/env bash

echo "==============================================="
echo "MongoDB post installation configuration started"
echo "==============================================="

mongo <<-EOF
  use $MONGO_ADMIN_AUTH_DATABASE
  db.auth("$MONGO_INITDB_ROOT_USERNAME", "$MONGO_INITDB_ROOT_PASSWORD")
  db.createUser({
    user: "$MONGO_ADMIN_USERNAME",
    pwd: "$MONGO_ADMIN_PASSWORD",
    roles: [
      {role: "userAdminAnyDatabase", db: "$MONGO_ADMIN_AUTH_DATABASE"},
      {role: "readWriteAnyDatabase", db: "$MONGO_ADMIN_AUTH_DATABASE"}
    ]
  })
EOF

echo "================================================"
echo "MongoDB post installation configuration finished"
echo "================================================"
