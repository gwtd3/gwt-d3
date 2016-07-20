#!/bin/bash
#
# Copyright (c) 2013, Anthony Schiochet and Eric Citaire
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#
# * Redistributions of source code must retain the above copyright notice, this
#   list of conditions and the following disclaimer.
#
# * Redistributions in binary form must reproduce the above copyright notice,
#   this list of conditions and the following disclaimer in the documentation
#   and/or other materials provided with the distribution.
#
# * The names Anthony Schiochet and Eric Citaire may not be used to endorse or promote products
#   derived from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL MICHAEL BOSTOCK BE LIABLE FOR ANY DIRECT,
# INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
# BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
# DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
# OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
# NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
# EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#


set -e

organization="gwtd3"
project_repo="gwt-d3"
site_repo="gwtd3.github.io"

if [ "${TRAVIS_PULL_REQUEST}" != "false" ] || [ "${TRAVIS_REPO_SLUG}" != "${organization}/${project_repo}" ] ; then
  echo "Not the official repo, skipping the deployment."
  exit 0
fi

build_dir="${PWD}/gwt-d3-demo/target"

encrypted_key="${encrypted_25c5d1a53c1c_key}"
encrypted_iv="${encrypted_25c5d1a53c1c_iv}"

git config --global user.email "build@travis-ci.org"
git config --global user.name "Travis-CI"
git config --global push.default simple

openssl aes-256-cbc -K "${encrypted_key}" -iv "${encrypted_iv}" -in id_rsa.enc -out id_rsa -d
chmod 400 id_rsa

cat <<EOT > ~/.ssh/config
host github.com
 HostName github.com
 IdentityFile ${PWD}/id_rsa
 User git
EOT

git clone "git@github.com:${organization}/${site_repo}.git"

cd "${site_repo}"

if [ -d demo ] ; then
  git rm -r demo
fi
mkdir demo
cd demo

unzip "${build_dir}/gwt-d3-demo.war" -x "META-INF/*" "WEB-INF/*"

git add .
git commit -m "Update demo"
git push

